import { ProductCartItemResponseDto, ProductResponse } from "./Product";
import { UserResponseDto } from "../types/User";

export interface CartItemRequestDto {
  userId: number;
  productId: number; // Long se mapea a number en TS
  cuantity: number;
}

export interface CartItemResponseDto {
  cartId: number; // Long se mapea a number
  user: UserResponseDto;
  product: ProductResponse;
  cuantity: number;
}

export interface CartItemUserResponseDto {
  product: ProductCartItemResponseDto;
  cuantity: number;
}
