The project structure is based on MVC Framework.
*Therefore, the database model files for USERAUTH, BUS, BUSSTOP, POI, ROUTE are stored in the Model Package (folder). USERINSTANCE class is a model which determines each user session.
*The Controllers (Adapters) which manage data delivery to views are stored in the Controller package. Eg: ALERTSADAPTER, BUSSTOPADAPTER, POIADAPTER.
*The Views (activities) which shows data on screen are stored in the view package. VOLLEYAPP class acts as controller for API REQUESTS to fetch the data and serve to models and views.
*MapsFragment sets up the map view and defines the logic for overlaying of markers.
*See Comments and TODOs inside the code




