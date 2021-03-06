// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiHost: 'http://52.187.48.147:9202/',
  gistHost: 'http://52.187.48.147:9203/',
  // apiHost: 'http://127.0.0.1:8080/',
  githubHost: 'https://api.github.com/',
  console: {
    host: 'http://52.187.48.147:9202/',
    hostws: 'ws://52.187.48.147:9202/'
    //  host: 'http://127.0.0.1:8080/'
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
