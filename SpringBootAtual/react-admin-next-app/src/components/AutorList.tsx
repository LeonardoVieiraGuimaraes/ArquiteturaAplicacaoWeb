import { List, Datagrid, TextField } from 'react-admin';

export const AutorList = (props: any) => (
  <List {...props}>
    <Datagrid>
      <TextField source="id" />
      <TextField source="nome" />
    </Datagrid>
  </List>
);
