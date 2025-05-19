import { IconButton } from "@mui/material";
import "../cardItemCard/CartItemStyles.css"; // Assuming you have a CSS file for styles
import { CartItemResponseDto } from "../../../service/CartItem.service.ts";

const CartItem = (cartItem: CartItemResponseDto) => {
  return (
    <div className="cartItem-container">
      <div className="cartItem-image-container">
        <img
          src={cartItem.product.imageImage}
          alt="Product"
          className="cartItem-image"
        />
      </div>
      <div className="cartItem-description-container">
        <h2 className="cartItem-name">{cartItem.product.productName}</h2>
        <h3 className="cartItem-price">Precio</h3>
        <p className="cartItem-price-text">{cartItem.product.price} tps.</p>
      </div>
      <div className="cartItem-actions-container">
        <IconButton
          className="cartItem-button"
          style={{
            backgroundColor: "#2f9e44",
            color: "secondary",
            borderRadius: 18,
            padding: "10px 20px",
            fontSize: "16px",
          }}
        />
        <select accessKey="" />
      </div>
    </div>
  );
};

export default CartItem;
