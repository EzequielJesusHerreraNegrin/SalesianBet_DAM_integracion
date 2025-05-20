import axios from "axios";
import { ApiResponseDto, baseURL } from "../types/api";
import { CartItemRequestDto, CartItemResponseDto } from "../types/cartItem";

const CART_ITEMS_RESOURCE = baseURL + "/cartItems"; // Ya incluye /api/v1 desde axios.baseURL
const token = localStorage.getItem("token"); // Obtener el token del localStorage

export const getAllCartItems = async (): Promise<
  ApiResponseDto<CartItemResponseDto[]>
> => {
  try {
    const response = await axios.get<ApiResponseDto<CartItemResponseDto[]>>(
      CART_ITEMS_RESOURCE,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: token ? `Bearer ${token}` : "",
        },
      }
    );
    console.log("Response from getAllCartItems:", response.data.code);

    return response.data; // Devolvemos directamente el array de ítems
  } catch (error) {
    console.error("Error fetching all cart items:", error);
    throw error; // Relanzar para que el componente que llama pueda manejarlo
  }
};

/**
 * Agrega un producto al carrito de un usuario.
 * POST /api/v1/cartItems/product/{userId}
 * @param userId ID del usuario
 * @param data Datos del ítem a agregar (productId, cuantity)
 */
export const addProductToCart = async (
  userId: number, // Long se mapea a number
  data: CartItemRequestDto
): Promise<ApiResponseDto<CartItemResponseDto>> => {
  try {
    const response = await axios.post<ApiResponseDto<CartItemResponseDto>>(
      `${CART_ITEMS_RESOURCE}/product/${userId}`,
      data
    );
    return response.data;
  } catch (error) {
    console.error(`Error adding product to cart for user ${userId}:`, error);
    throw error;
  }
};

/**
 * Actualiza un ítem en el carrito de un usuario.
 * PUT /api/v1/cartItems/product/{userId}
 * @param userId ID del usuario
 * @param data Datos del ítem a actualizar (productId, nueva cuantity)
 */
export const updateCartItem = async (
  userId: number, // Long se mapea a number
  data: CartItemRequestDto
): Promise<boolean> => {
  try {
    const response = await axios.put<ApiResponseDto<boolean>>(
      `${CART_ITEMS_RESOURCE}/product/${userId}`,
      data
    );
    return response.data.data; // Devuelve true si la operación fue exitosa
  } catch (error) {
    console.error(`Error updating cart item for user ${userId}:`, error);
    throw error;
  }
};

/**
 * Elimina un ítem del carrito de un usuario.
 * DELETE /api/v1/cartItems/{userId}/product/{productId}
 * @param userId ID del usuario
 * @param productId ID del producto a eliminar del carrito
 */
export const deleteCartItem = async (
  userId: number, // Long se mapea a number
  productId: number // Long se mapea a number
): Promise<boolean> => {
  try {
    const response = await axios.delete<ApiResponseDto<boolean>>(
      `${CART_ITEMS_RESOURCE}/${userId}/product/${productId}`
    );
    return response.data.data; // Devuelve true si la operación fue exitosa
  } catch (error) {
    console.error(
      `Error deleting cart item ${productId} for user ${userId}:`,
      error
    );
    throw error;
  }
};

export const cartItemService = {
  getAllCartItems,
  addProductToCart,
  updateCartItem,
  deleteCartItem,
};
