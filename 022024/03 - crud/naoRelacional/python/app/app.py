from flask import Flask
from flask_pymongo import PyMongo
from controllers.item_controller import item_bp, init_app
from dotenv import load_dotenv
import os

# Carregar variáveis de ambiente do arquivo .env
load_dotenv()

app = Flask(__name__)
app.config["MONGO_URI"] = os.getenv("MONGO_URI")
mongo = PyMongo(app)

# Inicializar o serviço e registrar o blueprint
init_app(mongo)
app.register_blueprint(item_bp, url_prefix='/items')

if __name__ == '__main__':
    app.run(debug=True)