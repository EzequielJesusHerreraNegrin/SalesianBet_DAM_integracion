import { useEffect, useState } from "react";
import CartItem from "../../components/Card/cardItemCard/CartItem";
import ProductCart from "../../components/Card/productCard/ProductCart";
import { cartItemService } from "../../service/cartItem.service";
import "../../service/product.service";
import ProductService from "../../service/product.service";
import UserService from "../../service/user.service";
import { CartItemResponseDto } from "../../types/cartItem";
import { Product } from "../../types/Product";
import "./StorePageStyles.css";
import { useAuthContext } from "../../context/AuthContext";

const StorePage = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [cartItems, setCartItems] = useState<CartItemResponseDto[]>([
    /*     {
      cartId: 1,
      user: { id: 1, username: "Juan" },
      product: {
        productId: 101,
        productName: "Producto A",
        price: 10.5,
        imageImage: "imageA.jpg",
      },
      cuantity: 2,
    },
    {
      cartId: 2,
      user: { id: 2, username: "Ana" },
      product: {
        productId: 102,
        productName: "Producto B",
        price: 20.0,
        imageImage: "imageB.jpg",
      },
      cuantity: 1,
    },
    {
      cartId: 3,
      user: { id: 1, username: "Juan" },
      product: {
        productId: 103,
        productName: "Producto C",
        price: 15.75,
        imageImage: "imageC.jpg",
      },
      cuantity: 3,
    }, */
  ]);
  const [searchFilter, setSearchFilter] = useState("");

  const { isAdmin } = useAuthContext();
  const { refreshUser } = useAuthContext();

  useEffect(() => {
    ProductService.getAllProducts()
      .then((product) => {
        if (product && Array.isArray(product)) {
          setProducts(product);
        } else {
          setProducts([]);
          console.error(
            "ProductService.getAllProducts() did not return an array in response.data:",
            product
          );
        }
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
        setProducts([]);
      });

    const handleGetCarIterms = async () => {
      const userToken = await UserService.manageUserToken();
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
  return (
    <div className="main-contaier">
      <div className="store-section-action">
        <label
          htmlFor="search"
          className="block text-sm font-medium text-gray-700 mb-1"
        >
          Buscar por nombre:
        </label>
        <input
          id="search"
          type="text"
          value={searchFilter}
          onChange={handleSearchChange}
          className="w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring focus:ring-blue-200"
          placeholder="Escribí un nombre..."
        />
      </div>
      <div className="products-grid">
        {products.map((product, index) => (
          <div key={index} className="product-card">
            <ProductCart
              product={product}
              cartItems={cartItems}
              setCartItems={setCartItems}
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
                .map((cartItem, index) => (
                  <div key={index} className="cartItem-card">
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
    </div>
  );
};

export default StorePage;
