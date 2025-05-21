export interface ApiResponseDto<T> {
  timestamp: Date;
  message: string;
  code: number; // HttpStatus.OK.value() es un int
  data: T;
}

export type JwtPayload = {
  sub: string;
  exp: number;
  iat: number;
  userId: number;
  role: string[];
};
