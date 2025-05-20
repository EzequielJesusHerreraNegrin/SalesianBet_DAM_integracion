import { ProductResponse } from "./Product";
import { UserEntityResponseDto } from "./User";

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
