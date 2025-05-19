import axios from "axios"; // Ajusta la ruta si es diferente
import { ProductResponse } from "../types/Product";

export interface UserEntityResponseDto {
  id: number; // Asumiendo que Long se mapea a number
  username: string;
}

export interface CartItemRequestDto {
  productId: number; // Long se mapea a number en TS
  cuantity: number;
}

export interface CartItemResponseDto {
  cartId: number; // Long se mapea a number
  user: UserEntityResponseDto;
  product: ProductResponse;
  cuantity: number;
}

export interface ApiResponseDto<T> {
  timestamp: Date;
  message: string;
  code: number; // HttpStatus.OK.value() es un int
  data: T;
}

const CART_ITEMS_RESOURCE = "/cartItems"; // Ya incluye /api/v1 desde axios.baseURL

export const getAllCartItems = async (): Promise<CartItemResponseDto[]> => {
  try {
    const response = await axios.get<ApiResponseDto<CartItemResponseDto[]>>(
      CART_ITEMS_RESOURCE
    );
    return response.data.data; // Devolvemos directamente el array de ítems
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
): Promise<boolean> => {
  try {
    const response = await axios.post<ApiResponseDto<boolean>>(
      `${CART_ITEMS_RESOURCE}/product/${userId}`,
      data
    );
    return response.data.data; // Devuelve true si la operación fue exitosa
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
