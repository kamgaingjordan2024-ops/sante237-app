import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function SelectCreneau({ onNavigate, rdvData, setRdvData }) {
  const [creneaux, setCreneaux] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const rdv = JSON.parse(localStorage.getItem("rdvData") || "{}");
    const medecinId = rdv?.medecin?.id_utilisateur;

    fetch(`${API}/api/creneaux`, {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => res.json())
      .then(data => {
        const liste = Array.isArray(data) ? data : [];
        console.log("Medecin ID:", rdv?.medecin?.id_utilisateur);
        console.log("Creneaux:", JSON.stringify(liste[0]));
        const filtres = liste.filter(c => c.disponible === true &&
           String (c.medecinId) === String(rdv?.medecin?.id_utilisateur)
        );
        setCreneaux(filtres);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, []);

  const handleSelect = (creneau) => {
    setRdvData({ ...rdvData, creneau });
    onNavigate("confirm-rdv");
  };

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

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>
      <div style={{ maxWidth: 700, margin: "0 auto" }}>

        <button onClick={() => onNavigate("select-medecin")} style={{
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
                background: i <= 3 ? "#1a5fd4" : "#e2e8f0",
                color: i <= 3 ? "white" : "#94a3b8",
                display: "flex", alignItems: "center", justifyContent: "center",
                fontSize: 12, fontWeight: 700,
              }}>
                {i < 3 ? "✓" : i + 1}
              </div>
              <span style={{ fontSize: 12, color: i <= 3 ? "#1a5fd4" : "#94a3b8", fontWeight: i === 3 ? 600 : 400 }}>
                {step}
              </span>
              {i < 4 && <div style={{ width: 20, height: 2, background: i < 3 ? "#1a5fd4" : "#e2e8f0" }} />}
            </div>
          ))}
        </div>

        {/* Résumé */}
        <div style={{
          background: "white", borderRadius: 12, padding: "12px 16px",
          marginBottom: 24, display: "flex", gap: 16, flexWrap: "wrap",
          boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
        }}>
          <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
            <span>🏥</span>
            <div>
              <div style={{ fontSize: 11, color: "#94a3b8" }}>Hopital</div>
              <div style={{ fontWeight: 600, fontSize: 13 }}>{rdvData?.hopital?.nom}</div>
            </div>
          </div>
          <div style={{ width: 1, background: "#e2e8f0" }} />
          <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
            <span>👨‍⚕️</span>
            <div>
              <div style={{ fontSize: 11, color: "#94a3b8" }}>Medecin</div>
              <div style={{ fontWeight: 600, fontSize: 13 }}>
                Dr. {rdvData?.medecin?.nom} {rdvData?.medecin?.prenom}
              </div>
            </div>
          </div>
        </div>

        <h1 style={{ fontSize: 28, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          Choisissez un creneau
        </h1>
        <p style={{ color: "#64748b", marginBottom: 24 }}>
          Selectionnez la date et l heure qui vous convient
        </p>

        {loading ? (
          <div style={{ textAlign: "center", padding: 48, color: "#64748b" }}>Chargement...</div>
        ) : creneaux.length === 0 ? (
          <div style={{
            textAlign: "center", padding: 48, color: "#64748b",
            background: "white", borderRadius: 16,
            boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
          }}>
            <div style={{ fontSize: 48, marginBottom: 16 }}>📅</div>
            <div style={{ fontWeight: 600, marginBottom: 8 }}>Aucun creneau disponible</div>
            <div style={{ fontSize: 14 }}>Ce medecin n a pas de creneaux disponibles</div>
          </div>
        ) : (
          <div style={{ display: "grid", gap: 12 }}>
            {creneaux.map(c => (
              <div key={c.idCreneau} onClick={() => handleSelect(c)} style={{
                background: "white", borderRadius: 16, padding: 20,
                boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
                cursor: "pointer", border: "2px solid transparent",
                transition: "all .2s",
                display: "flex", alignItems: "center", justifyContent: "space-between",
              }}
                onMouseEnter={e => e.currentTarget.style.border = "2px solid #1a5fd4"}
                onMouseLeave={e => e.currentTarget.style.border = "2px solid transparent"}
              >
                <div style={{ display: "flex", alignItems: "center", gap: 16 }}>
                  <div style={{
                    width: 52, height: 52, borderRadius: 12,
                    background: "linear-gradient(135deg, #dbeafe, #bfdbfe)",
                    display: "flex", alignItems: "center", justifyContent: "center",
                    fontSize: 24,
                  }}>📅</div>
                  <div>
                    <div style={{ fontWeight: 700, fontSize: 15, color: "#0f172a" }}>
                      {formatDate(c.dateHeure)}
                    </div>
                    <div style={{ fontSize: 14, color: "#1a5fd4", fontWeight: 600, marginTop: 4 }}>
                      {formatHeure(c.dateHeure)}
                    </div>
                    <div style={{ fontSize: 12, color: "#94a3b8", marginTop: 2 }}>
                      Duree: {c.duree} minutes
                    </div>
                  </div>
                </div>
                <div style={{
                  background: "#dcfce7", color: "#166534",
                  padding: "4px 12px", borderRadius: 99, fontSize: 12, fontWeight: 600,
                }}>
                  Disponible
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}