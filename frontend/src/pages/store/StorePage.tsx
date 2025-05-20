import { useEffect, useState } from "react";
import ProductCart from "../../components/Card/productCard/ProductCart";
import "./StorePageStyles.css";
import { Product } from "../../types/Product";
import CartItem from "../../components/Card/cardItemCard/CartItem";
import "../../service/product.service";
import ProductService from "../../service/product.service";
import { CartItemResponseDto } from "../../types/cartItem";
import { cartItemService } from "../../service/cartItem.service";
import { LocalStorageService } from "../../service/localstorage.service";
import { jwtDecode } from "jwt-decode";

const StorePage = () => {
  const decodedToken = LocalStorageService.get(
    LocalStorageService.KEY.userToken
  ).then((token) => {
    if (token) {
      const decodedToken = jwtDecode(token);
      console.log("Decoded token:", decodedToken);
    } else {
      console.log("No token found");
    }
  });

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

    cartItemService
      .getAllCartItems()
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
  }, []);

  return (
    <div className="main-contaier">
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
      {decodedToken.role === "ADMIN" && (
        <div className="admin-section">
          <h2 className="admin-section-title">Administración</h2>
          <button
            className="admin-section-button"
            onClick={() => {
              // Aquí puedes agregar la lógica para la administración
              console.log("Administrar productos");
            }}
          >
            Administrar productos
          </button>
        </div>
      )}
      <div className="cartItems-section-container">
        <h2 className="cartItems-section-title">Carrito de compras</h2>
        {cartItems.length === 0 ? (
          <div className="empty-cart-message">
            No hay productos en el carrito
          </div>
        ) : (
          <div className="cartItem-list">
            {cartItems.map((cartItem, index) => (
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
        <div className="cartItems-section-action-container">
          <button
            className="cartItems-section-action-button"
            style={{
              backgroundColor: "#2f9e44",
              color: "white",
              borderRadius: 18,
              padding: "10px 20px",
              fontSize: "16px",
            }}
          >
            Comprar
          </button>
        </div>
      </div>
    </div>
  );
};

export default StorePage;
