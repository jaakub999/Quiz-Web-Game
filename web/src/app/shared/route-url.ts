export enum RouteUrl {
  HOME = 'home',
  AUTH = 'login',
  REGISTER = 'register',
  PASSWORD_TOKEN = 'password/:token',
  EXPLORER = 'explorer',
  CREATOR = 'creator'
}

export namespace RouteUrl {
  export function convertFor(name: string): RouteUrl {
    switch (name) {
      case 'home':
        return RouteUrl.HOME;
      case 'login':
        return RouteUrl.AUTH;
      case 'register':
        return RouteUrl.REGISTER;
      case 'password/:token':
        return RouteUrl.PASSWORD_TOKEN;
      case 'explorer':
        return RouteUrl.EXPLORER;
      case 'creator':
        return RouteUrl.CREATOR;
    }

    return RouteUrl.HOME;
  }
}
