import { useState } from "react";
import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import SelectHopital from "./pages/rdv/SelectHopital";
import SelectSpecialite from "./pages/rdv/SelectSpecialite";
import SelectMedecin from "./pages/rdv/SelectMedecin";
import SelectCreneau from "./pages/rdv/SelectCreneau";
import ConfirmRdv from "./pages/rdv/ConfirmRdv";

export default function App() {
  const [page, setPage] = useState("home");
  const [rdvData, setRdvData] = useState({});

  const updateRdvData = (data) => {
    setRdvData(data);
    localStorage.setItem("rdvData", JSON.stringify(data));
  };

  if (page === "login" || page === "register") {
    return <LoginPage onNavigate={setPage} onSuccess={() => setPage("select-hopital")} />;
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

  return <LandingPage onNavigate={setPage} />;
}