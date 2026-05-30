import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function SelectMedecin({ onNavigate, rdvData, setRdvData }) {
  const [medecins, setMedecins] = useState([]);
  const [loading, setLoading] = useState(true);

 useEffect(() => {
  const token = localStorage.getItem("token");
  const rdv = JSON.parse(localStorage.getItem("rdvData") || "{}");

  fetch(`${API}/api/medecins`, {
    headers: { Authorization: `Bearer ${token}` }
  })
    .then(res => res.json())
    .then(data => {
      const liste = Array.isArray(data) ? data : [];
const filtres = liste.filter(m =>
  m.hopitalNom === rdv?.hopital?.nom &&
  m.specialites?.includes(rdv?.specialite?.nom)
);
      setMedecins(filtres.length > 0 ? filtres : liste);
      setLoading(false);
    })
    .catch(() => setLoading(false));
}, []);

  const handleSelect = (medecin) => {
    setRdvData({ ...rdvData, medecin });
    onNavigate("select-creneau");
  };

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>
      <div style={{ maxWidth: 700, margin: "0 auto" }}>

        <button onClick={() => onNavigate("select-specialite")} style={{
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
                background: i <= 2 ? "#1a5fd4" : "#e2e8f0",
                color: i <= 2 ? "white" : "#94a3b8",
                display: "flex", alignItems: "center", justifyContent: "center",
                fontSize: 12, fontWeight: 700,
              }}>
                {i < 2 ? "✓" : i + 1}
              </div>
              <span style={{ fontSize: 12, color: i <= 2 ? "#1a5fd4" : "#94a3b8", fontWeight: i === 2 ? 600 : 400 }}>
                {step}
              </span>
              {i < 4 && <div style={{ width: 20, height: 2, background: i < 2 ? "#1a5fd4" : "#e2e8f0" }} />}
            </div>
          ))}
        </div>

        {/* Résumé */}
        <div style={{
          background: "white", borderRadius: 12, padding: "12px 16px",
          marginBottom: 24, display: "flex", gap: 16,
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
            <span>🩺</span>
            <div>
              <div style={{ fontSize: 11, color: "#94a3b8" }}>Specialite</div>
              <div style={{ fontWeight: 600, fontSize: 13 }}>{rdvData?.specialite?.nom}</div>
            </div>
          </div>
        </div>

        <h1 style={{ fontSize: 28, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          Choisissez un medecin
        </h1>
        <p style={{ color: "#64748b", marginBottom: 24 }}>
          Selectionnez le medecin avec qui vous souhaitez consulter
        </p>

        {loading ? (
          <div style={{ textAlign: "center", padding: 48, color: "#64748b" }}>Chargement...</div>
        ) : medecins.length === 0 ? (
          <div style={{ textAlign: "center", padding: 48, color: "#64748b" }}>
            Aucun medecin disponible
          </div>
        ) : (
          <div style={{ display: "grid", gap: 16 }}>
            {medecins.map(m => (
              <div key={m.id_tilisateur} onClick={() => handleSelect(m)} style={{
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
                    width: 52, height: 52, borderRadius: "50%",
                    background: "linear-gradient(135deg, #1a5fd4, #38bdf8)",
                    display: "flex", alignItems: "center", justifyContent: "center",
                    color: "white", fontWeight: 700, fontSize: 18,
                  }}>
                    {m.nom?.charAt(0)}{m.prenom?.charAt(0)}
                  </div>
                  <div>
                    <div style={{ fontWeight: 700, fontSize: 16, color: "#0f172a" }}>
                      Dr. {m.nom} {m.prenom}
                    </div>
                    <div style={{ fontSize: 13, color: "#64748b", marginTop: 2 }}>
                      {m.specialites?.map(s => s.nom).join(", ")}
                    </div>
                    <div style={{ fontSize: 12, color: "#94a3b8", marginTop: 2 }}>
                      {m.hopitalNom}
                    </div>
                  </div>
                </div>
                <div style={{ color: "#1a5fd4", fontSize: 20 }}>›</div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}