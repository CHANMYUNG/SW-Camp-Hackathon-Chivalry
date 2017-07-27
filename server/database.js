let mysql = require('mysql');
let config = require('./config');


exports.connect = function(app){
    let conn = mysql.createConnection({
    host: config.db.host,
    user: config.db.user,
    password: config.db.password,
    database: config.db.database
    });

    app.set('db', conn);
}