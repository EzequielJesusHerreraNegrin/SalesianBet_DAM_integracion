import { Button } from "@mui/material";
import "../productCard/ProuctCartStyles.css"; // Assuming you have a CSS file for styles
import { Product, ProductCartItem } from "../../../types/Product";

const ProductCart = (Product: Product, cartItems: [ProductCartItem]) => {
  return (
    <div className="product-cart-container">
      <div className="product-image-container">
        <img src={Product.imageImage} alt="Product" className="product-image" />
      </div>
      <div className="product-description-container">
        <h2 className="product-name">{Product.productName}</h2>
        <h3 className="product-price">Precio</h3>
        <p className="product-price-text">{Product.price} tps.</p>
      </div>
      <div className="button-container"></div>
      <div className="product-button-container">
        <Button
          variant="contained"
          color="primary"
          className="product-button"
          style={{
            backgroundColor: "#2f9e44",
            color: "white",
            borderRadius: 18,
            padding: "10px 20px",
            fontSize: "16px",
          }}
          onClick={() => {
            return Product.productId;
          }}
        >
          Comprar
        </Button>
      </div>
    </div>
  );
};

export default ProductCart;
