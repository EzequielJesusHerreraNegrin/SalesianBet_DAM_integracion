import { Button } from "@mui/material";
import { cartItemService } from "../../../service/cartItem.service";
import { Product } from "../../../types/Product";
import {
  CartItemRequestDto,
  CartItemResponseDto,
} from "../../../types/cartItem";
import ModeEditIcon from "@mui/icons-material/ModeEdit";
import "../productCard/ProuctCartStyles.css"; // Assuming you have a CSS file for styles
import UserService from "../../../service/user.service";
import { useAuthContext } from "../../../context/AuthContext";

type Props = {
  product: Product;
  cartItems: CartItemResponseDto[];
  setCartItems: React.Dispatch<React.SetStateAction<CartItemResponseDto[]>>;
  onEdit?: (product: Product) => void;
};

const ProductCart = ({ product, cartItems, setCartItems, onEdit }: Props) => {
  const { isAdmin } = useAuthContext();

  const handleAddToCart = async (productToAdd: Product) => {
    cartItems.forEach((item) => {
      if (item.product!.productId == productToAdd.productId) {
        console.error("El producto ya está en el carrito");
        return;
      }
    });

    const token = await UserService.manageUserToken();
    const productCartItem: CartItemRequestDto = {
      userId: token!.userId,
      productId: productToAdd.productId,
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

    try {
      const response = await cartItemService.addProductToCart(productCartItem);
      // Asumiendo que response.data es CartItemResponseDto
      // Si el servicio ya devuelve el objeto directamente, usa 'response'
      if (response && response.data) {
        setCartItems((prevCartItems) => [...prevCartItems, response.data]);
      } else if (response) {
        // If response is not null but doesn't have a data property, handle gracefully or log an error
        console.error(
          "Unexpected response format when adding product to cart:",
          response
        );
      }
    } catch (error) {
      console.error("Error adding product to cart:", error);
    }
  };

  return (
    <div className="product-cart-container">
      {isAdmin && onEdit && (
        <Button
          size="small"
          onClick={() => onEdit(product)}
          style={{
            position: "absolute",
            top: 8,
            right: 8,
            zIndex: 1,
            minWidth: "auto",
            padding: "4px",
          }}
          title="Editar Producto"
        >
          <ModeEditIcon fontSize="small" />
        </Button>
      )}
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
      {!isAdmin && ( // Mostrar botón de añadir al carrito solo si NO es admin
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
            disabled={cartItems.some(
              (item) => item.product?.productId === product.productId
            )}
          >
            Añadir al carrito
          </Button>
        </div>
      )}
    </div>
  );
};

export default ProductCart;
