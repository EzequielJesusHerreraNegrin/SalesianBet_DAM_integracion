import axios from "axios";
import { ApiResponseDto } from "../types/api";
import { CartItemRequestDto, CartItemResponseDto } from "../types/cartItem";
import { API_URL } from "./api";
import { LocalStorageService } from "./localstorage.service";

const CART_ITEMS_RESOURCE = API_URL + "/cartItems"; // Ya incluye /api/v1 desde axios.baseURL

const getAllCartItems = async (): Promise<
  ApiResponseDto<CartItemResponseDto[]>
> => {
  try {
    const token = await LocalStorageService.get(
      LocalStorageService.KEY.userToken
    );

    const response = await axios.get<ApiResponseDto<CartItemResponseDto[]>>(
      CART_ITEMS_RESOURCE,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    console.log("Response from getAllCartItems:", response.data.code);

    return response.data;
  } catch (error) {
    console.error("Error fetching all cart items:", error);
    throw error;
  }
};

const getAllCartItemsByUserId = async (
  userId: number
): Promise<ApiResponseDto<CartItemResponseDto[]>> => {
  try {
    const token = await LocalStorageService.get(
      LocalStorageService.KEY.userToken
    );

    const response = await axios.get<ApiResponseDto<CartItemResponseDto[]>>(
      `${CART_ITEMS_RESOURCE}/${userId}`,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    console.log("Response from getAllCartItemsByUserId: ", response.data);

    return response.data;
  } catch (error) {
    console.error("Error fetching all cart items:", error);
    throw error;
  }
};

export const addProductToCart = async (
  data: CartItemRequestDto
): Promise<ApiResponseDto<CartItemResponseDto>> => {
  try {
    const token = await LocalStorageService.get(
      LocalStorageService.KEY.userToken
    );

    const response = await axios.post<ApiResponseDto<CartItemResponseDto>>(
      `${CART_ITEMS_RESOURCE}/product`,
      data,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error(
      `Error adding product to cart for user ${data.userId}:`,
      error
    );
    throw error;
  }
};

const updateCartItem = async (data: CartItemRequestDto): Promise<boolean> => {
  try {
    const token = await LocalStorageService.get(
      LocalStorageService.KEY.userToken
    );

    const response = await axios.put<ApiResponseDto<boolean>>(
      `${CART_ITEMS_RESOURCE}/product`,
      data,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data.data;
  } catch (error) {
    console.error(`Error updating cart item for user ${data.userId}:`, error);
    throw error;
  }
};

const deleteCartItem = async (
  userId: number,
  productId: number
): Promise<boolean> => {
  try {
    const token = await LocalStorageService.get(
      LocalStorageService.KEY.userToken
    );

    const response = await axios.delete<ApiResponseDto<boolean>>(
      `${CART_ITEMS_RESOURCE}/${userId}/product/${productId}`,
      {
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    console.log(response);

    return response.data.data;
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
  getAllCartItemsByUserId,
  addProductToCart,
  updateCartItem,
  deleteCartItem,
};
