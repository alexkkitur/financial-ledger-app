import {createRoot} from "react-dom/client"; import {CustomerPortal} from "./pages/CustomerPortal"; import {ComplianceDesk} from "./pages/ComplianceDesk"; import "./styles.css";
function App(){return <><nav><a href="#customer">Customer</a> <a href="#compliance">Compliance</a></nav>{location.hash==="#compliance"?<ComplianceDesk/>:<CustomerPortal/>}</>}
createRoot(document.getElementById("root")!).render(<App/>);
