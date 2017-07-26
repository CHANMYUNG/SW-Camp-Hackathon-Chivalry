let express = require('express');
let bodyParser = require('body-parser');
let sessionParser = require('express-session');
let morgan = require('morgan');
let database = require('./database');

let app = express();
let router = require('./routes');
app.set('port', 8080);

app.use(morgan('dev'));

app.use(bodyParser.urlencoded({
    extended : false
}));

app.use(sessionParser({
    key: 'sessionKey',
    secret: 'oi1uojooisd!@#*(@!#&(',
    resave: false
}));

app.use(bodyParser.json());
app.use('/',router);
app.listen(app.get('port'), function () {
    console.log("Started :: " + app.get('port'));
    database.connect(app);
});