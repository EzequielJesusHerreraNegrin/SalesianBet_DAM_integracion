import { ProductCartItem } from "../../../types/Product.ts";
import "../cardItemCard/CartItemStyles.css"; // Assuming you have a CSS file for styles

const CartItem = (cartItem: ProductCartItem) => {
  return (
    <div className="cartItem-container">
      <div className="cartItem-image-container">
        <img
          src={cartItem.productImage}
          alt="Product"
          className="cartItem-image"
        />
      </div>
      <div className="cartItem-description-container">
        <h2 className="cartItem-name">{cartItem.productName}</h2>
        <h3 className="cartItem-price">Precio</h3>
        <p className="cartItem-price-text">{cartItem.price} tps.</p>
      </div>
    </div>
  );
};

export default CartItem;
