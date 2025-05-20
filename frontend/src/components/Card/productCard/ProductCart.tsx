import { Button } from "@mui/material";
import "../productCard/ProuctCartStyles.css"; // Assuming you have a CSS file for styles
import { Product } from "../../../types/Product";
import { CartItemResponseDto } from "../../../types/cartItem";

type Props = {
  product: Product;
  cartItems: CartItemResponseDto[];
  setCartItems: React.Dispatch<React.SetStateAction<CartItemResponseDto[]>>;
};

const ProductCart = ({ product, cartItems, setCartItems }: Props) => {
  const handleAddToCart = (product: Product) => {
    const productCartItem: CartItemResponseDto = {
      cartId: cartItems.length, // Use a unique ID for the cart item
      user: { id: 1, username: "Juan" }, // Replace with actual user data
      product: {
        productId: product.productId,
        productName: product.productName,
        price: product.price,
        imageImage: product.imageImage,
      },
      cuantity: 1, // Default quantity
    };
    const updatedCart = [...cartItems, productCartItem];
    setCartItems(updatedCart);
    localStorage.setItem("cartItems", JSON.stringify(updatedCart));
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
