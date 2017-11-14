let express = require('express');
let bodyParser = require('body-parser');
let sessionParser = require('express-session');
let morgan = require('morgan');
let database = require('./database');

let app = express();
let router = require('./routes');
app.set('port', 8081);

app.use(morgan('dev'));

app.use(bodyParser.urlencoded({
    extended: false
}));

app.use(sessionParser({
    key: process.env.CHIVALRY_SESSION_KEY,
    secret: process.env.CHIVALRY_SECRET,
    resave: false
}));

app.use(bodyParser.json());
app.use('/', router);
app.listen(app.get('port'), function () {
    console.log("Started :: " + app.get('port'));
    database.connect(app);
});
