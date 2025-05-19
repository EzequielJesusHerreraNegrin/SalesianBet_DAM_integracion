import { useEffect, useState } from "react";
import ProductCart from "../../components/Card/productCard/ProductCart";
import "./StorePageStyles.css";
import { Product, ProductCartItem } from "../../types/Product";
import CartItem from "../../components/Card/cardItemCard/CartItem";
import "../../service/product.service";
import ProductService from "../../service/product.service";

const StorePage = () => {
  const [products, setProducts] = useState<Product[]>([]);

  const [cartItems, setCartItems] = useState<ProductCartItem[]>([]);

  useEffect(() => {
    ProductService.getAllProducts()
      .then((apiResponse) => {
        // Renombrado para claridad
        console.log("API Response:", apiResponse);
        if (apiResponse && Array.isArray(apiResponse)) {
          // Verifica apiResponse y apiResponse.data
          setProducts(apiResponse);
        } else {
          setProducts([]);
          console.error(
            "ProductService.getAllProducts() did not return an array in response.data:",
            apiResponse
          );
        }
      })
      .catch((error) => {
        // Buena práctica: manejar errores de la promesa
        console.error("Error fetching products:", error);
        setProducts([]);
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
      </div>
    </div>
  );
};

export default StorePage;
