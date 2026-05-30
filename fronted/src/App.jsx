import { useState } from "react";
import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import SelectHopital from "./pages/rdv/SelectHopital";
import SelectSpecialite from "./pages/rdv/SelectSpecialite";
import SelectMedecin from "./pages/rdv/SelectMedecin";
import SelectCreneau from "./pages/rdv/SelectCreneau";
import ConfirmRdv from "./pages/rdv/ConfirmRdv";
import DashboardMedecin from "./pages/medecin/DashboardMedecin";
import Notifications from "./pages/patient/Notifications";


export default function App() {
  const [page, setPage] = useState("home");
  const [rdvData, setRdvData] = useState({});

  const updateRdvData = (data) => {
    setRdvData(data);
    localStorage.setItem("rdvData", JSON.stringify(data));
  };

  // Redirection après connexion selon le rôle
  const handleLoginSuccess = () => {
    const user = JSON.parse(localStorage.getItem("user") || "{}");
    const role = user?.role;
    if (role === "MEDECIN") {
      setPage("dashboard-medecin");
    } else {
      setPage("select-hopital");
    }
  };

  if (page === "login" || page === "register") {
    return <LoginPage onNavigate={setPage} onSuccess={handleLoginSuccess} />;
  }

  if (page === "select-hopital") {
    return <SelectHopital onNavigate={setPage} rdvData={rdvData} setRdvData={updateRdvData} />;
  }

  if (page === "select-specialite") {
    return <SelectSpecialite onNavigate={setPage} rdvData={rdvData} setRdvData={updateRdvData} />;
  }

  if (page === "select-medecin") {
    return <SelectMedecin onNavigate={setPage} rdvData={rdvData} setRdvData={updateRdvData} />;
  }

  if (page === "select-creneau") {
    return <SelectCreneau onNavigate={setPage} rdvData={rdvData} setRdvData={updateRdvData} />;
  }

  if (page === "confirm-rdv") {
    return <ConfirmRdv onNavigate={setPage} rdvData={rdvData} setRdvData={updateRdvData} />;
  }

  if (page === "dashboard-medecin") {
    return <DashboardMedecin onNavigate={setPage} />;
  }
  
  if (page === "notifications") {
  return <Notifications onNavigate={setPage} />;
}

  return <LandingPage onNavigate={setPage} />;
}