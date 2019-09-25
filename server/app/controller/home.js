'use strict';

const Controller = require('egg').Controller;

class HomeController extends Controller {

  async index() {

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
    const tap = await execShellCommand(adb + " shell input tap 540 2022");

    ctx.body = devices

  }
}

module.exports = HomeController;
