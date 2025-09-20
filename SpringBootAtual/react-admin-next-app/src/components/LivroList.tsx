import { List, Datagrid, TextField } from 'react-admin';

export const LivroList = (props: any) => (
  <List {...props}>
    <Datagrid>
      <TextField source="id" />
      <TextField source="titulo" />
      <TextField source="autorId" />
    </Datagrid>
  </List>
);
