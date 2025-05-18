import { useEffect, useState } from "react";
import ProductCart from "../../components/Card/productCard/ProductCart";
import "./StorePageStyles.css";
import { ProductCartItem } from "../../types/Product";
import CartItem from "../../components/Card/cardItemCard/CartItem";

const StorePage = () => {
  const [products, setProducts] = useState([
    {
      productId: 1,
      productName: "Producto 1",
      description: "Descripción 1",
      price: 10000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 2,
      productName: "Producto 2",
      description: "Descripción 2",
      price: 15000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 3,
      productName: "Producto 3",
      description: "Descripción 3",
      price: 20000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 4,
      productName: "Producto 4",
      description: "Descripción 4",
      price: 25000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 5,
      productName: "Producto 5",
      description: "Descripción 5",
      price: 30000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 6,
      productName: "Producto 6",
      description: "Descripción 6",
      price: 35000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 7,
      productName: "Producto 7",
      description: "Descripción 7",
      price: 40000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 8,
      productName: "Producto 8",
      description: "Descripción 8",
      price: 45000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 9,
      productName: "Producto 9",
      description: "Descripción 9",
      price: 50000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      productId: 10,
      productName: "Producto 10",
      description: "Descripción 10",
      price: 55000,
      imageImage: "https://via.placeholder.com/150",
    },
  ]);

  const [cartItems, setCartItems] = useState<ProductCartItem[]>([]);

  useEffect(() => {
    const storedCartItems = localStorage.getItem("cartItems");
    if (storedCartItems) {
      setCartItems(JSON.parse(storedCartItems));
    }
    console.log(cartItems.length);
  }, [cartItems]);

  return (
    <div className="main-contaier">
      <div className="products-grid">
        {products.map((product, index) => (
          <div key={index} className="product-card">
            {ProductCart(product, cartItems)}
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
