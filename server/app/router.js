'use strict';

/**
 * @param {Egg.Application} app - egg application
 */
module.exports = app => {
  const { router, controller } = app;
  router.get('/', controller.home.index);
  router.get('/work', controller.home.startWork);

  router.get('/heartbeat', controller.home.heartbeat);
  router.get('/getProduceInfo', controller.home.getProduceInfo);
  router.post('/enterPassword', controller.home.enterPassword); // post

};
