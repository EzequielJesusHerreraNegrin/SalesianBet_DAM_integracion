export interface ApiResponseDto<T> {
  timestamp: Date;
  message: string;
  code: number; // HttpStatus.OK.value() es un int
  data: T;
}

export const baseURL = "http://localhost:8081/api/v1";
