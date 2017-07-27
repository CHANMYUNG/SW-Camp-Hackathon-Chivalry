let router = require('express').Router();
let util = require('../util');

router.route('/pain').get(function (req, res) {
    let db = req.app.get('db');
    db.query("SELECT * FROM reasonOfPain", function (err, rows) {
        if (err) res.status(500).end();
        let promise = new Promise(function (resolve, reject) {
            let response = [];
            for (var i = 0; i < rows.length; i++) {
                let item = {
                    "index": rows[i].index,
                    "title": rows[i].title,
                    "pain": rows[i].pain
                }
                response.push(item);
            }
            resolve(response);
        }).then((response) => {
            res.status(200).json(response);
        });
    })
});

router.route('/pain').put(function (req, res) {
    if (typeof req.body.stuNum === "undefined" || typeof req.body.pain === "undefined") {
        res.status(400).end();
    } else {
        // 대상 학생 학번
        let stuNum = req.body.stuNum;

        // 벌점
        let pain = req.body.pain;

        // 벌점 항목 번호
        let reason = req.body.reason;

        let db = req.app.get('db');

        let updatePromise = new Promise(function (resolve, reject) {
            db.query('select pain from student where stuNum = ?', stuNum, function (err, rows) {
                if (err) {
                    reject(500);
                } else {
                    let afterChangepain = Number(rows[0].pain) + Number(pain);
                    db.query('update student set pain = ? where stuNum = ?', [afterChangepain, stuNum], function (err, result) {
                        if(err) reject(500);
                        if (result.affectedRows === 1) {
                            resolve();
                        } else {
                            reject(400);
                        }
                    })
                }
            });
        });

        let historyPromise = new Promise(function (resolve, reject) {
            db.query("insert into history(hid, stuNum, type, reason, date) values((select COUNT(*) FROM history as h)+1,?,'pain',?,?)", [stuNum, reason, util.getDateNow()], function (err, updateResult) {
                if (err) {
                    reject(500);
                } else if (updateResult.affectedRows === 1) resolve();
                else {
                    reject(400);
                }
            })
        })

        Promise.all([updatePromise, historyPromise]).then(() => {
            res.status(200).end();
        }).catch(function (err) {
            res.status(400).end();
        })

    }
});

module.exports = router;