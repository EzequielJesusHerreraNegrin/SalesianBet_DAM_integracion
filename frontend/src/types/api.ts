export interface ApiResponseDto<T> {
  timestamp: Date;
  message: string;
  code: number; // HttpStatus.OK.value() es un int
  data: T;
}

export type ApiError = {
  timestamp: Date;
  status: number;
  message: string;
  errors: string[];
};

export type JwtPayload = {
  sub: string;
  exp: number;
  iat: number;
  userId: number;
  role: string[];
};
