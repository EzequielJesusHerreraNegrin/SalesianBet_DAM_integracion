import { Button } from "@mui/material";
import "../productCard/ProuctCartStyles.css"; // Assuming you have a CSS file for styles
import { Product, ProductCartItem } from "../../../types/Product";

const ProductCart = (product: Product, cartItems: ProductCartItem[]) => {
  const handleAddToCart = (product: Product) => {
    const cartItem: ProductCartItem = {
      productId: product.productId as number,
      productName: product.productName,
      productImage: product.imageImage,
      price: product.price,
    };
    cartItems.push(cartItem);
    console.log("Product added to cart:", cartItem);
  };
  return (
    <div className="product-cart-container">
      <div className="product-image-container">
        <img src={product.imageImage} alt="Product" className="product-image" />
      </div>
      <div className="product-description-container">
        <h2 className="product-name">{product.productName}</h2>
        <h3 className="product-price">Precio</h3>
        <p className="product-price-text">{product.price} tps.</p>
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
            handleAddToCart(product);
            console.log(cartItems.length);
          }}
        >
          Añadir al carrito
        </Button>
      </div>
    </div>
  );
};

export default ProductCart;
