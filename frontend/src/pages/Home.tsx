import {Link} from "react-router";
import "../styles/App.css";

function App() {
  return (
    <div className="dark:text-yellow-700">
      <form className="flex flex-col gap-10">
        <div></div>
        <button>Log In</button>
        <Link to="">Create new Account</Link>
        <Link to="">Forgot Password</Link>
      </form>
    </div>
  );
}

export default App;
