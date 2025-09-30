import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import EmployeeList from './components/EmployeesList';
import AddEditEmployee from './components/AddEditEmployee';
import ExtraHoursList from './components/ExtraHoursList';
import AddEditExtraHours from './components/AddEditExtraHours';
import NotFound from './components/NotFound';
import PaycheckList from './components/PaycheckList';
import PaycheckCalculate from './components/PaycheckCalculate';
import AnualReport from './components/AnualReport';
import AutorizationList from './components/AutorizationList';
import AddEditAutorizations from './components/AddEditAutorizations';
import JustificationList from './components/JustificationList';
import AddEditJustifications from './components/AddEditJustifications';
import InAndOutReg from './components/InAndOutReg';
import CalculateExtraHours from './components/CalculateExtraHour';
import CalculateLateArrivals from './components/CalculateLateArrivals';


function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/employee/list" element={<EmployeeList/>} />
              <Route path="/employee/add" element={<AddEditEmployee/>} />
              <Route path="/employee/edit/:id" element={<AddEditEmployee/>} />
              <Route path="/paycheck/list" element={<PaycheckList/>} />
              <Route path="/paycheck/calculate" element={<PaycheckCalculate/>} />
              <Route path="/reports/AnualReport" element={<AnualReport/>} />
              <Route path="/extraHours/list" element={<ExtraHoursList/>} />
              <Route path="/autorization/list" element={<AutorizationList/>} />
              <Route path="/extraHours/add" element={<AddEditExtraHours/>} />             
              <Route path="/extraHours/edit/:id" element={<AddEditExtraHours/>} />
              <Route path="/autorization/add" element={<AddEditAutorizations/>} />
              <Route path="/autorization/edit" element={<AddEditAutorizations/>} />
              <Route path="/justification/list" element={<JustificationList/>} />
              <Route path="/justification/add" element={<AddEditJustifications/>} />
              <Route path="/justification/edit" element={<AddEditJustifications/>} />
              <Route path="/inandoutreg" element={<InAndOutReg/>} />
              <Route path="/extrahour/calculate" element={<CalculateExtraHours/>} />
              <Route path="/latearrivals/calculate" element={<CalculateLateArrivals/>} />

              <Route path="*" element={<NotFound/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App;
