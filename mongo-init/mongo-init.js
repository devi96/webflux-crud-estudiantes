const adminRoleId = ObjectId();
const userRoleId = ObjectId();

db.roles.insertMany([
  { _id: adminRoleId, name: 'ADMIN' },
  { _id: userRoleId, name: 'USER' }
]);

db.users.insertMany([
  {
    _id: ObjectId(),
    username: 'admin',
    password: '$2a$12$zEj3aG9x1JO02MxGSlkB6.o/JkqQfYzaTKHJZ4ggntyqNfQTdbfQG', //hola,12
    status: true,
    roles: [{ _id: adminRoleId }]
  },
  {
    _id: ObjectId(),
    username: 'usuario',
    password: '$2a$12$CiJZlR7nG0sD5q.ODMtm7eDrgNEqAoHn6fVAE7s5DZhtCUEkaInPW',//mundo,12
    status: true,
    roles: [{ _id: userRoleId }]
  }
]);