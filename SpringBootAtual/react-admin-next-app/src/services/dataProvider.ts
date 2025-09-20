import simpleRestProvider from 'ra-data-simple-rest';
import keycloak from './keycloak';

const apiUrl = process.env.NEXT_PUBLIC_API_URL + '/';

const httpClient = async (url: string, options: any = {}) => {
  if (!options.headers) {
    options.headers = new Headers({ Accept: 'application/json' });
  }
  if (keycloak.token) {
    options.headers.set('Authorization', `Bearer ${keycloak.token}`);
  }
  const response = await fetch(url, options);
  const body = await response.text();
  let json;
  try {
    json = JSON.parse(body);
  } catch (e) {
    json = undefined;
  }
  return {
    status: response.status,
    headers: response.headers,
    body,
    json,
  };
};

const dataProvider = simpleRestProvider(apiUrl, httpClient);
export default dataProvider;
