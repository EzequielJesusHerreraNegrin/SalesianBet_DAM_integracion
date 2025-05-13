const KEY = {
  userToken: "user-token",
};

async function save<T>(key: string, value: T) {
  try {
    const jsonValue = JSON.stringify(value);
    localStorage.setItem(key, jsonValue);
  } catch (error) {
    console.error(`localstorage error: ${error}`);
  }
}

async function get(key: string) {
  try {
    const jsonValue = localStorage.getItem(key);
    return jsonValue != null ? JSON.parse(jsonValue) : null;
  } catch (error) {
    console.error(`localstorage error: ${error}`);
  }
}

async function remove(key: string) {
  try {
    localStorage.removeItem(key);
  } catch (error) {
    console.error(`localstorage error: ${error}`);
  }
}

export const LocalStorageService = {
  KEY,
  save,
  get,
  remove,
};
