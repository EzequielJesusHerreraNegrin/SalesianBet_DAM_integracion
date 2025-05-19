import { useEffect, useState } from "react";
import ProductCart from "../../components/Card/productCard/ProductCart";
import "./StorePageStyles.css";
import { Product } from "../../types/Product";
import CartItem from "../../components/Card/cardItemCard/CartItem";
import "../../service/product.service";
import ProductService from "../../service/product.service";
import {
  CartItemResponseDto,
  cartItemService,
} from "../../service/CartItem.service";

const StorePage = () => {
  const [products, setProducts] = useState<Product[]>([]);

  const [cartItems, setCartItems] = useState<CartItemResponseDto[]>([]);

  useEffect(() => {
    ProductService.getAllProducts()
      .then((product) => {
        // Renombrado para claridad
        console.log("API Response:", product);
        if (product && Array.isArray(product)) {
          // Verifica apiResponse y apiResponse.data
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
        // Buena práctica: manejar errores de la promesa
        console.error("Error fetching products:", error);
        setProducts([]);
      });

    cartItemService.getAllCartItems().then((apiResponse) => {
      // Renombrado para claridad
      console.log("API Response:", apiResponse);
      if (apiResponse && Array.isArray(apiResponse)) {
        // Verifica apiResponse y apiResponse.data
        setCartItems(apiResponse);
      } else {
        setCartItems([]);
        console.error(
          "cartItemService.getAllCartItems() did not return an array in response.data:",
          apiResponse
        );
      }
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
                {CartItem(cartItem)}
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
