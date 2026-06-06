import { useState } from "react";

const API = "http://localhost:8081";

export default function ConfirmRdv({ onNavigate, rdvData, setRdvData }) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);

  const formatDate = (dateHeure) => {
    if (!dateHeure) return "";
    const date = new Date(dateHeure);
    return date.toLocaleDateString("fr-FR", {
      weekday: "long", day: "numeric", month: "long", year: "numeric"
    });
  };

  const formatHeure = (dateHeure) => {
    if (!dateHeure) return "";
    const date = new Date(dateHeure);
    return date.toLocaleTimeString("fr-FR", { hour: "2-digit", minute: "2-digit" });
  };

  const handleConfirm = async () => {
    setLoading(true); setError("");
    const token = localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user") || "{}");
    const rdv = JSON.parse(localStorage.getItem("rdvData") || "{}");
console.log("CRENEAU complet:", rdv?.creneau);
    console.log("USER:", user);
console.log("RDV:", rdv);
console.log("Payload envoyé:", {
    patientId: user?.id_utilisateur || user?.idUtilisateur || user?.id,
    medecinId: rdv?.medecin?.id_utilisateur,
    creneauId: rdv?.creneau?.idCreneau,
    dateHeure: rdv?.creneau?.dateHeure,
});   
console.log("CRENEAU dans rdv:", rdv?.creneau);
console.log("Clés du creneau:", Object.keys(rdv?.creneau || {}));
try {
      const res = await fetch(`${API}/api/rendezvous`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
   body: JSON.stringify({
  patientId: parseInt(user?.id || user?.id_utilisateur || user?.idUtilisateur),
  medecinId: rdv?.medecin?.id_utilisateur || rdv?.medecin?.idUtilisateur,
 creneauId: rdv?.creneau?.id_creneau,
  dateHeure: rdv?.creneau?.dateHeure,
  duree: rdv?.creneau?.duree,
  statut: "PLANIFIE",
}),
      });

      if (res.ok) {
        setSuccess(true);
        localStorage.removeItem("rdvData");
      } else {
        const data = await res.json();
        setError(data.message || "Erreur lors de la confirmation.");
      }
    } catch {
      setError("Impossible de contacter le serveur.");
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div style={{
        minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)",
        display: "flex", alignItems: "center", justifyContent: "center", padding: 24,
      }}>
        <div style={{
          background: "white", borderRadius: 24, padding: 48,
          textAlign: "center", maxWidth: 480,
          boxShadow: "0 8px 32px rgba(26,95,212,0.12)",
        }}>
          <div style={{ fontSize: 72, marginBottom: 24 }}>✅</div>
          <h2 style={{ fontSize: 28, fontWeight: 700, color: "#0f172a", marginBottom: 12 }}>
            Rendez-vous confirme !
          </h2>
          <p style={{ color: "#64748b", marginBottom: 32, lineHeight: 1.7 }}>
            Votre rendez-vous a ete enregistre avec succes. Le medecin va confirmer votre RDV.
          </p>
          <button onClick={() => onNavigate("home")} style={{
            width: "100%", padding: "14px", borderRadius: 12, border: "none",
            background: "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
            color: "white", fontSize: 16, fontWeight: 700, cursor: "pointer",
          }}>
            Retour a l accueil
          </button>
        </div>
      </div>
    );
  }

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>
      <div style={{ maxWidth: 600, margin: "0 auto" }}>

        <button onClick={() => onNavigate("select-creneau")} style={{
          background: "none", border: "none", cursor: "pointer",
          color: "#1a5fd4", fontSize: 14, fontWeight: 600, marginBottom: 24,
        }}>
          Retour
        </button>

        {/* Progression */}
        <div style={{ display: "flex", alignItems: "center", gap: 8, marginBottom: 32 }}>
          {["Hopital", "Specialite", "Medecin", "Creneau", "Confirmation"].map((step, i) => (
            <div key={step} style={{ display: "flex", alignItems: "center", gap: 8 }}>
              <div style={{
                width: 28, height: 28, borderRadius: "50%",
                background: "#1a5fd4", color: "white",
                display: "flex", alignItems: "center", justifyContent: "center",
                fontSize: 12, fontWeight: 700,
              }}>
                {i < 4 ? "✓" : "5"}
              </div>
              <span style={{ fontSize: 12, color: "#1a5fd4", fontWeight: i === 4 ? 600 : 400 }}>
                {step}
              </span>
              {i < 4 && <div style={{ width: 20, height: 2, background: "#1a5fd4" }} />}
            </div>
          ))}
        </div>

        <h1 style={{ fontSize: 28, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          Confirmation du RDV
        </h1>
        <p style={{ color: "#64748b", marginBottom: 24 }}>
          Verifiez les details avant de confirmer
        </p>

        {/* Résumé complet */}
        <div style={{
          background: "white", borderRadius: 20, padding: 28,
          boxShadow: "0 4px 16px rgba(26,95,212,0.08)", marginBottom: 24,
        }}>
          {[
            { icon: "🏥", label: "Hopital", value: rdvData?.hopital?.nom },
            { icon: "🩺", label: "Specialite", value: rdvData?.specialite?.nom },
            { icon: "👨‍⚕️", label: "Medecin", value: `Dr. ${rdvData?.medecin?.nom} ${rdvData?.medecin?.prenom}` },
            { icon: "📅", label: "Date", value: formatDate(rdvData?.creneau?.dateHeure) },
            { icon: "🕐", label: "Heure", value: formatHeure(rdvData?.creneau?.dateHeure) },
            { icon: "⏱️", label: "Duree", value: `${rdvData?.creneau?.duree} minutes` },
          ].map((item, i) => (
            <div key={i} style={{
              display: "flex", alignItems: "center", gap: 16,
              padding: "14px 0",
              borderBottom: i < 5 ? "1px solid #f1f5f9" : "none",
            }}>
              <span style={{ fontSize: 24, width: 32 }}>{item.icon}</span>
              <div style={{ flex: 1 }}>
                <div style={{ fontSize: 12, color: "#94a3b8", marginBottom: 2 }}>{item.label}</div>
                <div style={{ fontWeight: 600, color: "#0f172a" }}>{item.value}</div>
              </div>
            </div>
          ))}
        </div>

        {error && (
          <div style={{
            padding: "12px 16px", borderRadius: 10,
            background: "#fee2e2", color: "#991b1b", fontSize: 13,
            marginBottom: 16,
          }}>
            {error}
          </div>
        )}

        <button onClick={handleConfirm} disabled={loading} style={{
          width: "100%", padding: "16px", borderRadius: 12, border: "none",
          background: loading ? "#94a3b8" : "linear-gradient(135deg, #1a5fd4, #0d3d8f)",
          color: "white", fontSize: 16, fontWeight: 700,
          cursor: loading ? "not-allowed" : "pointer",
          boxShadow: "0 4px 14px rgba(26,95,212,0.3)",
        }}>
          {loading ? "Confirmation..." : "Confirmer le rendez-vous"}
        </button>
      </div>
    </div>
  );
}