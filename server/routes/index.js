let router = require('express').Router();
let prizeRouter = require('./prize');
let painRouter = require('./pain');
let roomRouter = require('./room');
let patrolRouter = require('./patrol');

router.use('/', prizeRouter);
router.use('/', painRouter);
router.use('/', roomRouter);
router.use('/', patrolRouter);
module.exports = router;