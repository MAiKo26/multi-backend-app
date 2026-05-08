# Express Backend

A Node.js/Express REST API with WebSocket support.

## Prerequisites

- **Node.js** 22+
- **pnpm** (recommended) or npm
- **Database**: PostgreSQL (or SQLite for development)

## Quick Start

```bash
cd express

# Install dependencies
pnpm install

# Copy environment file and configure
cp .env.example .env
# Edit .env with your database credentials

# Start development server
pnpm run dev
```

The API will be available at `http://localhost:3000`

## Available Scripts

| Command | Description |
|---------|-------------|
| `pnpm run dev` | Start development server with hot reload |
| `pnpm run build` | Compile TypeScript to JavaScript |
| `pnpm run start` | Start production server |

## Installing Packages

```bash
# Add a new dependency
pnpm add <package-name>

# Add a dev dependency
pnpm add -D <package-name>
```

## Environment Variables

Create a `.env` file in the root directory:

```env
PORT=3000
DATABASE_URL=postgresql://user:password@localhost:5432/mydb
JWT_SECRET=your-secret-key
```

## Project Structure

```
express/
├── src/
│   ├── index.ts         # Entry point
│   ├── db/              # Database config & schema
│   ├── routes/          # API routes
│   ├── controllers/     # Business logic
│   ├── middleware/     # Auth, rate limiting
│   └── utils/           # Helpers
├── drizzle.config.ts    # ORM config
└── package.json
```