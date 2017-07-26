let router = require('express').Router();
let util = require('../util');


router.route('/prize').get(function (req, res) {
    
});
router.route('/prize').put(function (req, res) {
    if (typeof req.body.stuNum === "undefined" || typeof req.body.prize === "undefined") {
        res.status(400).end();
    } else {
        // 대상 학생 학번
        let stuNum = req.body.stuNum;

        // 벌점
        let prize = req.body.prize;

        // 벌점 항목 번호
        let reason = req.body.reason;

        let db = req.app.get('db');

        let updatePromise = new Promise(function (resolve, reject) {
            db.query('select prize from student where stuNum = ?', stuNum, function (err, rows) {
                if (err) {
                    reject(500);
                } else {
                    let afterChangePrize = Number(rows[0].prize) + Number(prize);
                    db.query('update student set prize = ? where stuNum = ?', [afterChangePrize, stuNum], function (err, result) {
                        if (err) reject(500);
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
            db.query("insert into history(hid, stuNum, type, reason, date) values((select COUNT(*) FROM history as h)+1,?,'prize',?,?)", [stuNum, reason, util.getDateNow()], function (err, updateResult) {
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