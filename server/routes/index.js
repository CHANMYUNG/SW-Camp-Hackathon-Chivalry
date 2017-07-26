let router = require('express').Router();
let prizeRouter = require('./prize');
let painRouter = require('./pain');
let roomRouter = require('./room');


router.use('/', prizeRouter);
router.use('/', painRouter);
router.use('/', roomRouter);

module.exports = router;