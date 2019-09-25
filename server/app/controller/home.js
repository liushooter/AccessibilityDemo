'use strict';
const exec = require('child_process').exec;

const Controller = require('egg').Controller;

class HomeController extends Controller {
  async index() {
    const { ctx } = this;

    const child = exec('ls -l | wc -l',
        (error, stdout, stderr) => {
            console.log(`stdout: ${stdout}`);
            console.log(`stderr: ${stderr}`);

            if (error !== null) {
                console.log(`exec error: ${error}`);
            }
    });

    ctx.body = 'hi, egg';
  }
}

module.exports = HomeController;
