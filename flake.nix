{
  description = "Devshell for Java.";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = {
    self,
    nixpkgs,
  }: let
    system = "x86_64-linux";
    pkgs = import nixpkgs {inherit system;};

    jdkWithFX = pkgs.openjdk.override {
      enableJavaFX = true;
      openjfx_jdk = pkgs.openjfx.override {withWebKit = true;};
    };
  in {
    devShells.${system}.default = pkgs.mkShell {
      # Add packages here.
      buildInputs = with pkgs; [
        charm-freeze
        google-java-format
        jdkWithFX
        just
        maven
        python312Packages.img2pdf
      ];

      shellHook = ''
        echo "Entering the development environment!"
        java --version
      '';
    };
  };
}
