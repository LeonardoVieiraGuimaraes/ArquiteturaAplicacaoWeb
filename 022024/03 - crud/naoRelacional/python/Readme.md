Configurar o ambiente:

Criar um novo projeto Django.
Instalar as dependências necessárias (django, django-ninja, djongo).
Configurar o projeto Django:

Configurar o banco de dados MongoDB no settings.py.
Criar um aplicativo Django para gerenciar os dados.
Definir o modelo Django:

Criar um modelo para os dados que serão armazenados no MongoDB.
Configurar Django Ninja:

Configurar Django Ninja para criar as rotas CRUD.
mkdir crud-django
cd crud-django
python -m venv venv
source venv/bin/activate  # No Windows use `venv\Scripts\activate`

django-admin startproject myproject .


Passo 2: Configurar o projeto Django
Edite o arquivo myproject/settings.py para configurar o banco de dados MongoDB:
# myproject/settings.py
DATABASES = {
    'default': {
        'ENGINE': 'djongo',
        'NAME': 'cruddb',
    }
}

Crie um aplicativo Django:

python manage.py startapp crud