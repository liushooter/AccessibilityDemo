'use strict';

const sleep = require('sleep');
const Controller = require('egg').Controller;

class HomeController extends Controller {

  async index() {
    const { ctx } = this;

    ctx.body = "hi egg";

  }

// TODO
// 控制器开始工作
  async startWork() {

  }

// TODO
// 心跳服务 是否在线
  async heartbeat() {

  }


// TODO
// 心跳服务 是否在线
  async getProduceInfo() {
    const { ctx } = this;

      const proce = this.ctx.query;
      console.log(proce);
      console.log("price: " +  proce.price);

      ctx.body = proce;

  }

// TODO
// 心跳服务 是否在线
  async enterPassword() {
    function execShellCommand(cmd) {
      // https://medium.com/@ali.dev/how-to-use-promise-with-exec-in-node-js-a39c4d7bbf77

      const exec = require('child_process').exec;

      return new Promise((resolve, reject) => {
        exec(cmd, (error, stdout, stderr) => {
          if (error) {
            console.err(error);
          }

          resolve(stdout ? stdout : stderr);

        });
      });
    }

    const { ctx } = this;

    var adb = "/Users/shooter/Library/Android/sdk-macosx/platform-tools/adb";
    const devices = await execShellCommand(adb + " devices");

    await execShellCommand(adb + " shell input tap 540 2022");
    // sleep.msleep(500)

    await execShellCommand(adb + " shell input tap 157 1864");
    // sleep.msleep(500)

    await execShellCommand(adb + " shell input tap 499 1699");
    // sleep.msleep(100)

    await execShellCommand(adb + " shell input tap 872 1540");
    // sleep.msleep(500)

    await execShellCommand(adb + " shell input tap 858 1670");
    // sleep.msleep(500)

    await execShellCommand(adb + " shell input tap 890 1879");
    // sleep.msleep(500)

    console.log("enterPassword controller");

    ctx.body = devices;

  }

}

module.exports = HomeController;
