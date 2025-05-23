import { useEffect, useState } from "react";
import CartItem from "../../components/Card/cardItemCard/CartItem";
import ProductCart from "../../components/Card/productCard/ProductCart";
import { cartItemService } from "../../service/cartItem.service";
import "../../service/product.service";
import ProductService from "../../service/product.service";
import UserService from "../../service/user.service";
import { CartItemResponseDto } from "../../types/cartItem";
import { Product, ProductRequest } from "../../types/Product";
import "./StorePageStyles.css";
import { useAuthContext } from "../../context/AuthContext";
import { Button } from "@mui/material";
import ProductModal from "../../components/form/product/ProductModal";
const StorePage = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [cartItems, setCartItems] = useState<CartItemResponseDto[]>([]);
  const [searchFilter, setSearchFilter] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);

  const { isAdmin } = useAuthContext();
  const { refreshUser } = useAuthContext();

  useEffect(() => {
    ProductService.getAllProducts()
      .then((productData) => {
        if (productData && Array.isArray(productData)) {
          setProducts(productData);
        } else {
          setProducts([]);
          console.error(
            "ProductService.getAllProducts() did not return an array in response.data:",
            productData
          );
        }
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
        setProducts([]);
      });

    const handleGetCarIterms = async () => {
      const userToken = await UserService.manageUserToken();
      if (!userToken) {
        setCartItems([]); // Si no hay token, no hay carrito para este usuario
        return;
      }
      cartItemService
        .getAllCartItemsByUserId(userToken!.userId)
        .then((response) => {
          if (response && Array.isArray(response)) {
            setCartItems(response);
          } else if (response && Array.isArray(response.data)) {
            setCartItems(response.data);
          } else {
            setCartItems([]);
            console.error(
              "cartItemService.getAllCartItems() did not return an array or array in response.data:",
              response
            );
          }
        })
        .catch((error) => {
          console.error("Error fetching cart items:", error);
          setCartItems([]);
        });
    };

    handleGetCarIterms();
  }, [cartItems.length, products.length]);

  const handleBuyCartItemList = async () => {
    const token = await UserService.manageUserToken();
    await UserService.buyCartItems(token!.userId)
      .then(() => {
        setCartItems([]);
        refreshUser();
      })
      .catch((error) => {
        throw new Error(error);
      });
  };

  const basketValue = () => {
    let finalvalue = 0;
    cartItems.forEach((item) => {
      if (item.product != null) {
        finalvalue += item.product.price * item.cuantity;
      }
    });
    return finalvalue;
  };

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchFilter(event.target.value);
  };

  const filteredProducts = products.filter((item) =>
    item.productName.toLowerCase().includes(searchFilter.toLowerCase())
  );

  const handleOpenCreateModal = () => {
    setEditingProduct(null);
    setIsModalOpen(true);
  };

  const handleOpenEditModal = (product: Product) => {
    setEditingProduct(product);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEditingProduct(null);
  };

  const handleProductSubmit = async (
    productData: ProductRequest,
    productId?: number
  ) => {
    try {
      if (productId) {
        // Editar producto
        await ProductService.updateProduct(productId, productData);
        console.log("Product updated successfully");
      } else {
        // Crear producto
        await ProductService.createProduct(productData);
        console.log("Product created successfully");
      }
      // Recargar la lista de productos
      const updatedProducts = await ProductService.getAllProducts();
      if (updatedProducts && Array.isArray(updatedProducts)) {
        setProducts(updatedProducts);
      } else {
        setProducts([]);
      }
      handleCloseModal(); // Cerrar el modal
    } catch (error) {
      console.error("Error saving product:", error);
      // Aquí podrías mostrar un error en el modal o como notificación
      throw error; // Re-throw para que el modal sepa que hubo un error
    }
  };

  return (
    <div className="main-contaier">
      <div className="store-section-action">
        <div className="store-section-action-filter">
          <label htmlFor="search" className="search-label">
            Buscar por producto:
          </label>
          <input
            id="search"
            type="text"
            value={searchFilter}
            onChange={handleSearchChange}
            className="search-input"
          />
        </div>
        {isAdmin ? (
          <Button
            variant="contained"
            color="success"
            onClick={handleOpenCreateModal} // Abre el modal para crear
          >
            AÑADIR PRODUCTO
          </Button>
        ) : null}
        <hr className="section-lines" />
      </div>
      <div className={`products-grid ${isAdmin ? "admin-full-width" : ""}`}>
        {filteredProducts.map((product) => (
          <div key={product.productId} className="product-card">
            <ProductCart
              product={product}
              cartItems={cartItems}
              setCartItems={setCartItems}
              onEdit={() => handleOpenEditModal(product)}
              // onDelete={() => handleDeleteProduct(product.productId)}
            />
          </div>
        ))}
      </div>
      {isAdmin ? null : (
        <div className="cartItems-section-container">
          <h2 className="cartItems-section-title">Carrito de compras</h2>
          {cartItems.length === 0 ? (
            <div className="empty-cart-message">
              No hay productos en el carrito
            </div>
          ) : (
            <div className="cartItem-list">
              {cartItems
                .filter((item) => item?.product)
                .map((cartItem) => (
                  <div key={cartItem.cartId} className="cartItem-card">
                    <CartItem
                      cartItem={cartItem}
                      setCartItems={setCartItems}
                      cartItems={cartItems}
                    />
                  </div>
                ))}
            </div>
          )}
          <div className="cartItems-section-footer">
            <div className="cartItems-section-footer-resume">
              {cartItems.length > 0 ? (
                <p>Importe total : {basketValue()} pts.</p>
              ) : null}
            </div>
            <div className="cartItems-section-action-container">
              <button
                className="cartItems-section-action-button"
                disabled={cartItems.length == 0}
                onClick={() => handleBuyCartItemList()}
              >
                COMPRAR
              </button>
            </div>
          </div>
        </div>
      )}
      {isAdmin && (
        <ProductModal
          open={isModalOpen}
          onClose={handleCloseModal}
          onSubmit={handleProductSubmit}
          initialData={editingProduct}
        />
      )}
    </div>
  );
};

export default StorePage;
