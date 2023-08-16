const http = require("http");
const fs = require("fs");
const fsPromises = require("fs").promises;
const PORT = process.env.PORT || 8001;

const server = http.createServer((req, res) => {
  console.log(req.url, req.method);
});

server.listen(PORT, () => console.log(`server runnign on port ${PORT}`));
