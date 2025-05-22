import { IconButton } from "@mui/material";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import { CartItemResponseDto } from "../../../types/cartItem";
import "../cardItemCard/CartItemStyles.css"; // Assuming you have a CSS file for styles
import { cartItemService } from "../../../service/cartItem.service";
import UserService from "../../../service/user.service";

type CartItemProps = {
  cartItem: CartItemResponseDto;
  cartItems: CartItemResponseDto[];
  setCartItems: React.Dispatch<React.SetStateAction<CartItemResponseDto[]>>;
};

const CartItem = ({ cartItem, cartItems, setCartItems }: CartItemProps) => {
  const handleDelete = async (productId: number) => {
    const updatedCartItems = cartItems.filter(
      (item) => item.product.productId !== productId
    );
    const token = await UserService.manageUserToken();
    await cartItemService
      .deleteCartItem(token!.userId, productId)
      .then(() => {
        console.log("eleminado");

        setCartItems(updatedCartItems);
      })
      .catch((error) => {
        throw new Error(error);
      });
  };

  const handleQuantityChange = (cartItemId: number, newQuantity: number) => {
    const updatedCartItems = cartItems.map((item) =>
      item.cartId === cartItemId ? { ...item, cuantity: newQuantity } : item
    );
    setCartItems(updatedCartItems);
  };

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
        <h2 className="cartItem-description-name">
          {cartItem.product.productName}
        </h2>
        <h3 className="cartItem-description-price">Precio</h3>
        <p className="cartItem-description-price-text">
          {cartItem.product.price} tps.
        </p>
      </div>
      <div className="cartItem-actions-container">
        <IconButton
          className="cartItem-actions-delete-button"
          children={<DeleteOutlineIcon color="error" />}
          onClick={() => {
            handleDelete(cartItem.product.productId);
          }}
          aria-label="delete"
        />
        <input
          className="cartItem-actions-quantity"
          value={cartItem.cuantity}
          onChange={(e) => {
            const newQuantity = parseInt((e.target as HTMLInputElement).value);
            handleQuantityChange(cartItem.cartId, newQuantity);
          }}
          min="1"
          max="99"
          step="1"
          type="number"
        />
      </div>
    </div>
  );
};

export default CartItem;
