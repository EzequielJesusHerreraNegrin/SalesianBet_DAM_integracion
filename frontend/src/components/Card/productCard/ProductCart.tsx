import { Button } from "@mui/material";
import { cartItemService } from "../../../service/cartItem.service";
import { Product } from "../../../types/Product";
import {
  CartItemRequestDto,
  CartItemResponseDto,
} from "../../../types/cartItem";
import "../productCard/ProuctCartStyles.css"; // Assuming you have a CSS file for styles
import UserService from "../../../service/user.service";

type Props = {
  product: Product;
  cartItems: CartItemResponseDto[];
  setCartItems: React.Dispatch<React.SetStateAction<CartItemResponseDto[]>>;
};

const ProductCart = ({ product, cartItems, setCartItems }: Props) => {
  const handleAddToCart = async (product: Product) => {
    console.log(cartItems[0]);

    cartItems.forEach((item) => {
      if (item.product!.productId == product.productId) {
        console.error("El producto ya está en el carrito");
        return;
      }
    });

    const token = await UserService.manageUserToken();
    const productCartItem: CartItemRequestDto = {
      userId: token!.userId,
      productId: product.productId,
      cuantity: 1,
    };

    await cartItemService
      .addProductToCart(productCartItem)
      .then((response) => {
        setCartItems([...cartItems, response.data]);
      })
      .catch((error) => {
        throw new Error(error);
      });
  };

  return (
    <div className="product-cart-container">
      <div className="product-image-container">
        <img
          src={
            "https://res.cloudinary.com/dtbgfrolh/image/upload/v1747970900/store/camiseta-oficial-FCB.png"
          }
          alt="Product"
          className="product-image"
        />
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
          }}
        >
          Añadir al carrito
        </Button>
      </div>
    </div>
  );
};

export default ProductCart;
