import { useState } from "react";
import ProductCart from "../../components/Card/productCard/ProductCart";
import "./StorePageStyles.css";
import { ProductCartItem } from "../../types/Product";

const StorePage = () => {
  const [products, setProducts] = useState([
    {
      name: "Producto 1",
      description: "Descripción 1",
      price: 10000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 2",
      description: "Descripción 2",
      price: 15000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 3",
      description: "Descripción 3",
      price: 20000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 4",
      description: "Descripción 4",
      price: 25000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 5",
      description: "Descripción 5",
      price: 30000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 6",
      description: "Descripción 6",
      price: 35000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 7",
      description: "Descripción 7",
      price: 40000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 8",
      description: "Descripción 8",
      price: 45000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 9",
      description: "Descripción 9",
      price: 50000,
      imageImage: "https://via.placeholder.com/150",
    },
    {
      name: "Producto 10",
      description: "Descripción 10",
      price: 55000,
      imageImage: "https://via.placeholder.com/150",
    },
  ]);

  const [cartItems, setCartItems] = useState<[ProductCartItem]>([]);

  return (
    <div className="main-contaier">
      <div className="products-grid">
        {products.map((product, index) => (
          <div key={index} className="product-card">
            <ProductCart
              imageImage={product.imageImage}
              productName={product.name}
              price={product.price}
            />
          </div>
        ))}
      </div>
      <div className="cartItem-container"></div>
    </div>
  );
};

export default StorePage;
