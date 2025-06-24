module.exports = [
  {
    context: ['/api'],
    target: 'http://localhost:9900',
    secure: false,
    changeOrigin: true,
    headers: {
      'X-Request-ID': 'f4a4fd8d-43ae-4365-bb71-038c34d06881',
      'Origin': 'http://localhost:4200',
    },
  },
];
