import { Admin, Resource } from 'react-admin';
import simpleRestProvider from 'ra-data-simple-rest';
import { useEffect } from 'react';
import keycloak from '../services/keycloak';
import { AutorList } from '../components/AutorList';
import { LivroList } from '../components/LivroList';

const dataProvider = simpleRestProvider(process.env.NEXT_PUBLIC_API_URL + '/');

export default function AdminPanel() {
  useEffect(() => {
    keycloak.init({ onLoad: 'login-required' });
  }, []);

  return (
    <Admin dataProvider={dataProvider}>
  <Resource name="autores" list={AutorList} />
  <Resource name="livros" list={LivroList} />
    </Admin>
  );
}
